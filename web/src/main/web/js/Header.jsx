import React from 'react';

import PureSolTechnologies from './components/PureSolTechnologies';

export default function Header() {
    return (
        <header className="row">
            <div className="col-md-6">
                <h1>
                    Streaming Library
                </h1>
            </div>
            <div className="col-md-6" style={{ textAlign: 'right' }}>
                <div>
                    <PureSolTechnologies /><br />
                    <span className="credo">Because&nbsp;software&nbsp;quality begins&nbsp;at&nbsp;the&nbsp;source.</span>
                </div>
            </div>
        </header>
    );
}
