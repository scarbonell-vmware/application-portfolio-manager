import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { useState, useEffect } from 'react';
const LoadTester = ({ }) => {

    const [memoryUsage, setMemoryUsage] = React.useState();
    const [memoryAllocated, setMemoryAllocated] = React.useState();
    const [memoryTarget, setMemoryTarget] = React.useState();
    const [cpuUsage, setCpuUsage] = React.useState();
    const [cpuTestStatus, setCpuTestStatus] = React.useState();
    const [memoryTestStatus, setMemoryTestStatus] = React.useState();

    function handleChangeTarget(event) {
        setMemoryTarget(event.target.value)
    }


    function updateUsage () {
        fetchMemoryUsage()
        fetchCPUUsage()
    }
    const fetchMemoryUsage = async () => {
        await fetch('/api/memoryUsed', {
            method: 'GET'
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setMemoryUsage(data)
          });
      }

      const fetchMemoryAllocated = async () => {
        await fetch('/api/memoryAllocated', {
            method: 'GET'
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setMemoryAllocated(data)
          });
      }

      const fetchCPUUsage = async () => {
        await fetch('/api/cpuUsage', {
            method: 'GET'
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setCpuUsage(data)
          });
      }

      const startCPUTest = async () => {
        await fetch('/api/increaseCPU', {
            method: 'POST'
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setCpuTestStatus(data)
          });
      }

      const startMemoryTest = async () => {
        await fetch('/api/increaseMemory', {
            method: 'POST',
            body: memoryTarget,
            headers: {
                'Accept': 'plain/text',
                'Content-Type' : 'text/plain;charset=UTF-8'
              }
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setMemoryTestStatus(data)
          });
      }

      const stopAllTests = async () => {
        await fetch('/api/stopLoadTests', {
            method: 'POST'
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setMemoryTestStatus(data)
            setCpuTestStatus(data)
          });
      }
   
       // First load we get the templates available
    useEffect(() => {
        fetchMemoryAllocated()
        updateUsage()
        const intervalId = setInterval(updateUsage, 2000);
    
        return () => {
          if (intervalId) {
            clearInterval(intervalId)
          }
        }
      }, [])

    return (

      <div>
          <div className='usage'>
          <div>Memory Allocated: <span style={{'fontWeight': 'bold'}}>{memoryAllocated}</span></div>
          <div>Memory Usage: <span style={{'fontWeight': 'bold'}}>{memoryUsage}</span></div>
          <div>CPU Usage: <span style={{'fontWeight': 'bold'}}>{cpuUsage}</span></div>
          </div>
       <div>
       <div className='test'>
           <Button style={{'fontSize': '13px'}} onClick={() => startCPUTest()} variant="outlined">Start CPU Test</Button>
           <div>{cpuTestStatus}</div>
           </div>
           <div className='test'>
 
           <div>Memory Target (M): {memoryTarget}</div>
           <TextField style={{height: '70px',fontSize: "12px"}} variant="outlined" onChange={handleChangeTarget} value={memoryTarget} id="memoryTarget"  />
           <Button style={{'fontSize': '13px'}} onClick={() => startMemoryTest()} variant="outlined">Start Memory Test</Button>
           <div>{memoryTestStatus}</div>
           </div>
           <div className='test'>
           <Button style={{'fontSize': '13px'}} onClick={() => stopAllTests()} variant="outlined">Stop Tests</Button>
 
           </div>
       </div>
      </div>

    );
};

export default LoadTester;