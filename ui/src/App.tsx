
import React from 'react';
import Header from './Components/Header';
import Navigation from './Components/Navigation';
import { useEffect } from 'react';
import MainPage from "./MainPage";
import { useState } from 'react';
const App: React.FC<{ }> = () => {

  const [navigationState, setNavigationState] = useState<any>({})
  const [version, setVersion] = useState<any>()
  function updateNavigationState (state: any) {
    if (state) {
       setNavigationState(state)
    }
  }

  const fetchVersion = async () => {
    await fetch('/api/version', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).then(function (response) {
        return response.json();
      })
      .then(function (data) {
        setVersion(data)
      });
  }
  useEffect(() => {
    fetchVersion()
  }, [])
  //--------------------------------------------------------
  return (
    <React.Fragment>
    {version && <Header version = {version} navigationCallBack={updateNavigationState}></Header>}
    <div className='layout'>
      <Navigation />
      <MainPage navigationState={navigationState} updateNavigationState={updateNavigationState}/>
    </div>
    </React.Fragment>
  );
}
        
export default App;