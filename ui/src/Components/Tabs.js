import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Tab from './Tab';
import './Tabs.css'

class Tabs extends Component {
    static propTypes = {
      children: PropTypes.instanceOf(Array).isRequired,
    }
  
    constructor(props) {
      super(props);
      this.state = {
        activeTab: this.props.children[0].props.title
      };
    }
  
    onClickTabItem = (tab) => {
      this.setState({ activeTab: tab });
    }
    render() {
        const {
          onClickTabItem,
          props: {
            children,
          },
          state: {
            activeTab,
          }
        } = this;
    
        return (
          <div className="tabs">
            <ol className="tab-list">
              {children.map((child) => {
                const { title, about } = child.props;
                return (
                  <Tab
                    activeTab={activeTab}
                    key={title}
                    title={title}
                    about={about}
                    onClick={onClickTabItem}
                  />
                );
              })}
            </ol>
            <div className="tab-content">
            {children.map((child) => {
                if (child.props.title !== activeTab) return undefined;
                return <div className='tab-description'>{child.props.about}</div>
              })}
              {children.map((child) => {
                if (child.props.title !== activeTab) return undefined;
                return child.props.children;
              })}
            </div>
          </div>
        );
      }
    }

export default Tabs;