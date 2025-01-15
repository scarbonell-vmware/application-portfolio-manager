import React from 'react'
import styled from 'styled-components'
import { useTable, useFilters, useAsyncDebounce } from 'react-table'
import { useState, useEffect } from 'react';
const Styles = styled.div`
  padding: 1rem;
  label {
      font-weight: bold;
      border-bottom: 1px solid black;
  }
  ul {
      margin-left: 10px;
  }
  table {
   
    border-spacing: 0;
    border: 1px solid black;

    tr {
      :last-child {
        td {
          border-bottom: 0;
        }
      }
    }

    th,
    td {
      margin: 0;
      padding: 0.5rem;
      font-size: 11px;
      border-bottom: 1px solid black;
      border-right: 1px solid black;
      vertical-align: text-top;
      :last-child {
        border-right: 0;
      }
    }
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
      placeholder={`Search ${count} records...`}
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
      <table className='filtered-table' {...getTableProps()}>
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

  //applications.push({"name" : "Some App"})

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
      }
    ],
  }]

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
    <div className="main-table">
        <Table columns={columns} data={data} />
    </div>
    </Styles>
  )
}

export default ApplicationList
