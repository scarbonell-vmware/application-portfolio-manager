import React from 'react';
import { useState, useEffect } from 'react';
import ApplicationList from './Components/ApplicationList';

const MainPage: React.FC<{ navigationState: any, updateNavigationState: any }> = ({ navigationState, updateNavigationState }) => {


  // First load we get the templates available
  useEffect(() => {
  }, [])

  return (
    <React.Fragment>
       <ApplicationList></ApplicationList>
    </React.Fragment>
  );
};
export default MainPage;