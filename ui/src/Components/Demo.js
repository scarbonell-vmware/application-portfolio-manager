
import React from "react";
import { useState, useEffect } from 'react';
import PropTable from './PropTable';
const Demo = () => {
    const [activeProfile, setActiveProfile] = React.useState();
    const [envProps, setEnvProps] = React.useState();
    const [appProps, setAppProps] = React.useState();

    const fetchActiveProfile = async () => {
        await fetch('/api/activeProfiles', {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
            }
          }).then(function (response) {
            return response.text();
          })
          .then(function (data) {
            setActiveProfile(data)
          });
      }

      const fetchEnvProps = async () => {
        await fetch('/api/envProps', {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
            }
          }).then(function (response) {
            return response.json();
          })
          .then(function (data) {
              console.log(data.VCAP_APPLICATION)
            if (data.VCAP_APPLICATION) data.VCAP_APPLICATION = JSON.parse(data.VCAP_APPLICATION)
            if (data.VCAP_SERVICES) data.VCAP_SERVICES = JSON.parse(data.VCAP_SERVICES)
            console.log(data)
            setEnvProps(data)
          });
      }

      const fetchAppProps = async () => {
        await fetch('/api/appProps', {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
            }
          }).then(function (response) {
            return response.json();
          })
          .then(function (data) {
            setAppProps(data)
          });
      }
    
      useEffect(() => {
        fetchActiveProfile()
        fetchEnvProps()
        fetchAppProps()
      }, [])

    return (

      <>
      <div className="section-title">Active Profile: {activeProfile}</div>
      <div className="section-title">Application Properties:</div>
      {appProps && <PropTable props={appProps}></PropTable>}
      <div className="section-title">Environment Properties:</div>
      {envProps && <PropTable props={envProps}></PropTable>}
      <div className="section-title">TAS Application Properties:</div>
      {envProps && envProps.VCAP_APPLICATION && <PropTable props={envProps.VCAP_APPLICATION}></PropTable>}
      
      <div className="section-title">TAS Service Properties:</div>
      {envProps && envProps.VCAP_SERVICES && <PropTable props={envProps.VCAP_SERVICES}></PropTable>}

      </>

    );
};

export default Demo;