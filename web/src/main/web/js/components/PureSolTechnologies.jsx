import React from 'react';
import { Link } from 'react-router-dom'

export default function PureSolTechnologies() {
    return (
        <Link className="navbar-brand" to="/">
            <div className="logo"><span className="puresol">PureSol</span> <span className="technologies">Technologies</span></div>
        </Link>
    );
}
