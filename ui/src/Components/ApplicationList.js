import React from 'react'
import styled from 'styled-components'
import { useTable, useFilters, useAsyncDebounce } from 'react-table'
import { useState, useEffect } from 'react';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import Grid from "@material-ui/core/Grid";
import CloseIcon from "@material-ui/icons/Close";
import IconButton from "@material-ui/core/IconButton";
import Button from '@material-ui/core/Button';
import Typography from "@material-ui/core/Typography";
import ApplicationForm from './ApplicationForm';
import Demo from './Demo';
import Tabs from './Tabs';
import LoadTester from './LoadTester';

const Styles = styled.div`
  padding: 1rem;
  label {
      font-weight: bold;
      border-bottom: 1px solid black;
  }  
`

// Define a default UI for filtering
function GlobalFilter({
  preGlobalFilteredRows,
  globalFilter,
  setGlobalFilter,
}) {
  const count = preGlobalFilteredRows.length
  const [value, setValue] = React.useState(globalFilter)
  const onChange = useAsyncDebounce(value => {
    setGlobalFilter(value || undefined)
  }, 200)

  return (
    <span>
      Search:{' '}
      <input
        value={value || ""}
        onChange={e => {
          setValue(e.target.value);
          onChange(e.target.value);
        }}
        placeholder={`${count} records...`}
        style={{
          fontSize: '1.1rem',
          border: '0',
        }}
      />
    </span>
  )
}

function EmptyFilter({
  column: { filterValue = [], preFilteredRows, setFilter, id },
}) {
  return (<div></div>)
}

// Define a default UI for filtering
function DefaultColumnFilter({
  column: { filterValue, preFilteredRows, setFilter }
}) {
  const count = preFilteredRows.length
   
  return (
    <input
      value={filterValue || ''}
      onChange={e => {
        setFilter(e.target.value || undefined) // Set undefined to remove the filter entirely
      }}
      placeholder={``}
    />
  )
}

// Our table component
function Table({ columns, data }) {
  const filterTypes = React.useMemo(
    () => ({
      // Or, override the default text filter to use
      // "startWith"
      customText: (rows, id, filterValue) => {
        return rows.filter(row => {
          const rowValue = row.values[id]
          return rowValue !== undefined
            ? String(rowValue.name)
              .toLowerCase()
              .includes(String(filterValue).toLowerCase())
            : true
        })
      }
    }),
    []
  )

  const defaultColumn = React.useMemo(
    () => ({
      // Let's set up our default Filter UI
      Filter: DefaultColumnFilter,
    }),
    []
  )

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    state,
    visibleColumns,
    preGlobalFilteredRows,
    setGlobalFilter,
  } = useTable(
    {
      columns,
      data,
      defaultColumn, // Be sure to pass the defaultColumn option
      filterTypes,
    },
    useFilters
  )

  // We don't want to render all of the rows for this example, so cap
  // it for this use case
  const firstPageRows = rows.slice(0, 1000)

  return (
    <>
      <table className='styled-table' {...getTableProps()}>
        <thead>
          {headerGroups.map(headerGroup => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map(column => (
                <th {...column.getHeaderProps()}>
                  {column.render('Header')}
                  {/* Render the columns filter UI */}
                  <div>{column.canFilter ? column.render('Filter') : null}</div>
                </th>
              ))}
            </tr>
          ))}

        </thead>
        <tbody {...getTableBodyProps()}>
          {firstPageRows.map((row, i) => {
            prepareRow(row)
            return (
              <tr {...row.getRowProps()}>
                {row.cells.map(cell => {
                  return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                })}
              </tr>
            )
          })}
        </tbody>
      </table>
    </>
  )
}

function ApplicationList() {

  const [applications, setApplications] = React.useState([]);
  const [currentApplication, setCurrentApplication] = React.useState();
  const [displayCreateDialog, setDisplayCreateDialog] = React.useState(false);
  const [displayEditDialog, setDisplayEditDialog] = React.useState(false);

  function openCreateDialog () {
    setDisplayCreateDialog(true)
  }

  function closeCreateDialog () {
    setDisplayCreateDialog(false)
  }

  function openEditDialog (application) {
    setCurrentApplication(application)
    setDisplayEditDialog(true)
  }

  function closeEditDialog () {
    setDisplayEditDialog(false)
  }

  const deleteApp = async (id) => {
    await fetch(`/api/v1/application/${id}`, {
      method: 'DELETE'
    })
    .then(function (data) {
      fetchApplications()
    });
  }

  const createApp = async (application) => {
    await fetch('/api/v1/application', {
      method: 'POST',
      body: JSON.stringify(application),
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then(function (data) {
      fetchApplications()
      closeCreateDialog()
    });
  }

  const updateApp = async (application) => {
    await fetch('/api/v1/application', {
      method: 'PUT',
      body: JSON.stringify(application),
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then(function (data) {
      fetchApplications()
      closeEditDialog()
    });
  }

  const defaultColumns = [{
    Header: 'Applications',
    columns: [
      {
        Header: 'Identifier',
        accessor: "identifier"
      },
      {
        Header: 'Name',
        accessor: "name"
      },
      {
        Header: 'Description',
        accessor: "description"
      },
      {
        Header: 'Organization',
        accessor: "organization"
      },
      {
        Header: 'Business Unit',
        accessor: "businessUnit"
      },
      {
        Header: 'Business Owner',
        accessor: "businessOwner"
      },
      {
        Header: 'Actions',
        accessor: r => r,
        Cell: displayActions,
        Filter: EmptyFilter
      }
    ],
  }]

  function displayActions({ value }) {
    return <div className='action-items'>
      <div onClick={() => openEditDialog(value)} title="Edit" className='edit-on'></div>
      <div onClick={() => deleteApp(value.id)} title="Delete" className='delete-on'></div>
      
      </div>
      
  }

  const columns = React.useMemo(
    () => defaultColumns,
    []
  )

  const data = React.useMemo(() => {
    return applications
  })

const fetchApplications = async () => {
    await fetch('/api/v1/application', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).then(function (response) {
        return response.json();
      })
      .then(function (data) {
        setApplications(data)
      });
  }

  useEffect(() => {
    fetchApplications()
  }, [])

  return (
    <Styles>
      <Tabs>
        <div title='Manage Applications' >
            <div className="main-table">
                <Button style={{'fontSize': '13px'}} onClick={() => openCreateDialog()} variant="outlined">Create Application</Button>
                <Table columns={columns} data={data} />
            </div>
       </div>

       <div title='Manage Organizations' ></div>
       <div title='Manage Users' ></div>
       <div title='Settings' ></div>
       <div title='Properties' >
         <Demo></Demo>
       </div>
       <div title='Load Testing' >
         <LoadTester></LoadTester>
       </div>
    </Tabs>
    {displayCreateDialog && <Dialog open={true} onClose={closeCreateDialog}>
        <DialogTitle>
          <Grid container justify="space-between" alignItems="center">
          <Typography variant="div">Create new Application</Typography>
            <IconButton onClick={() => closeCreateDialog()}>
              <CloseIcon />
            </IconButton>
          </Grid></DialogTitle>

          <ApplicationForm onClose={closeCreateDialog} onSubmit={createApp} title={'Create Application'}></ApplicationForm>
     
      </Dialog>}
      {displayEditDialog && <Dialog open={true} onClose={closeEditDialog}>
        <DialogTitle>
          <Grid container justify="space-between" alignItems="center">
          <Typography variant="div">Update Application {currentApplication.name}</Typography>
            <IconButton onClick={() => closeCreateDialog()}>
              <CloseIcon />
            </IconButton>
          </Grid></DialogTitle>

          <ApplicationForm onClose={closeEditDialog} application={currentApplication} onSubmit={updateApp} title={'Udpate Application'+currentApplication.name}></ApplicationForm>
     
      </Dialog>}
    </Styles>
  )
}

export default ApplicationList
