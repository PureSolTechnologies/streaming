import React from 'react';
import { Link } from 'react-router-dom'

import TwitterTimeline from '../components/social/TwitterTimeline';
import PureSolTechnologies from '../components/PureSolTechnologies';

export default function Home() {
    return (
        <div className="row">
            <div className="col-md-6">
                <p>
                    For applications processing a lot of data, streaming raw data and iterating of the data is crucial for performance, latency and small memory footprint.
                </p>
                <h2>Iterators</h2>
                <p>
                    Iterators are a structured way to access structured data. In Java itself, some arithmetic capabilities are missing, which are provided here. <Link to="/iterators">Read more...</Link>
                </p>
                <h2>Streams</h2>
                <p>
                    The stream API in Java suffers from compatibility issues to former versions. Classes are not performant, functionalty is missing and some arithmetic is missing. <Link to="/streams">Read more...</Link>
                </p>
                    <h2>Binary Processing</h2>
                    <p>
                        Java is not very good and performant, when it comes to handle binary files. An easy way to access binary data is provided as extension to the streaming library. <Link to="/binary">Read more...</Link>
                    </p>
                        <h2>CSV Processing</h2>
                        <p>
                            An easy way to read and create CSV files is provided as extension to the streaming library. <Link to="/csv">Read more...</Link>
                        </p>
            </div >
            <div className="col-md-6">
                <h2>News</h2>
                <TwitterTimeline />
            </div>
        </div >
    );
}
