import React from 'react';
import { Link } from 'react-router-dom'
import PureSolTechnologies from './components/PureSolTechnologies';

var octicons = require( "octicons" )

export default function Menu() {
    return (
        <div className="nnavbar navbar-expand-lg navbar-light bg-light">
            <button className="navbar-toggler" type="button"
                data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item"><Link className="nav-link" to="/"><div dangerouslySetInnerHTML={{ __html: octicons.home.toSVG() }} /></Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/iterators">Iterators</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/streams">Streams</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/binary">Binary</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/csv">CSV</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/imprint">Imprint</Link></li>
                </ul>
            </div>
        </div>
    );
}
