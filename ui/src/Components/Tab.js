import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Tab extends Component {
    static propTypes = {
      activeTab: PropTypes.string.isRequired,
      title: PropTypes.string.isRequired,
      onClick: PropTypes.func.isRequired,
    };
  
    onClick = () => {
      const { title, onClick } = this.props;
      onClick(title);
    }
  
    render() {
      const {
        onClick,
        props: {
          activeTab,
          title,
        },
      } = this;
  
      let className = 'tab-list-item';
  
      if (activeTab === title) {
        className += ' tab-list-active';
      }
  
      return (
        <li
          className={className}
          onClick={onClick}
        >
          {title}
        </li>
      );
    }
  }
  
  export default Tab;