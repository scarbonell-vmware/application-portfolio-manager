import React from 'react';
import { useState, useEffect } from 'react';
const Navigation = ({}) => {



    return (
        <nav className="navigation">
            <ul>
               
                <li>
                    <a className='api-doc-off' href="/swagger-ui/index.html" target='_blank' value="Filter"/>
                </li>
                <li>
                    <a className='api-graphql-doc-off' href="/graphiql" target='_blank' value="Filter"/>
                </li>
                <li>
                    <a className='plugin-off' value="Filter"/>
                </li>
                <li>
                    <a className='grouptag-off' value="Filter"/>
                </li>
                <li>
                   <a className='info-off'  value="Filter"/>
                </li>
               
            </ul>
        </nav>
    );
};
export default Navigation;