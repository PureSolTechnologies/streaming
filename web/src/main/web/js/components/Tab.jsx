import React from 'react';
import PropTypes from 'prop-types';

export default class Tab extends React.Component {

    static propTypes = {
        heading: PropTypes.string.isRequired
    };

    constructor( props ) {
        super( props );
    }

    render() {
        return (
            this.props.selected == this.props.tabId ? <div>{this.props.children}</div> : null
        );
    }
}
