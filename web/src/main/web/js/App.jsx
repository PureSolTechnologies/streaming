import React from 'react';
import { Switch, Route } from 'react-router-dom'

import Footer from './Footer';
import Header from './Header';
import Menu from './Menu';

import Binary from './pages/Binary';
import CSV from './pages/CSV';
import Streams from './pages/Streams';
import Home from './pages/Home';
import Imprint from './pages/Imprint';
import Iterators from './pages/Iterators';


const App = () => {
    return (
        <div className="container">
            <Header />
            <Menu />
            <Switch>
                <Route exact path='/' component={Home} />
                <Route path='/iterators' component={Iterators} />
                <Route path='/streams' component={Streams} />
                <Route path='/binary' component={Binary} />
                <Route path='/csv' component={CSV} />
                <Route path='/imprint' component={Imprint} />
            </Switch>
            <Footer />
        </div>
    );
};

export default App;