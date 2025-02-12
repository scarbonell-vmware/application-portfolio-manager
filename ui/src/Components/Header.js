import React from 'react';
import { useState, useEffect } from 'react';
import AboutDialog from './AboutDialog';
const Header = ( {navigationCallBack, version} ) => {
    const [instanceIndex, setInstanceIndex] = useState("...")
    const [displayAboutDialog, setDisplayAboutDialog] = useState(false)
   
    const onAboutDialogClose = () => {
      setDisplayAboutDialog(false)
   }

   const onAboutDialogOpen = () => {
       setDisplayAboutDialog(true)
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
             
            if (data.VCAP_APPLICATION) {
                data.VCAP_APPLICATION = JSON.parse(data.VCAP_APPLICATION)
                console.log(data.VCAP_APPLICATION)
                setInstanceIndex(data.VCAP_APPLICATION.instance_index)
                
            }
            
          });
      }
      useEffect(() => {
        fetchEnvProps()
      }, [])
    return (
        <header className='header'>
            <div className="flex-container">
                <div className='logo'></div>
                <div className='title'>Application Portfolio Manager <span id="version">{version.name} .NET CORE</span></div>
                <div className='instance-index'>{'TAS Instance: '+instanceIndex}</div>
                <div className='about-bt' onClick={onAboutDialogOpen}></div>
            </div>
            {displayAboutDialog && <AboutDialog version={version} onClose={onAboutDialogClose} ></AboutDialog>}
        </header>
    );
};
export default Header;