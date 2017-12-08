import React from 'react';
import { Link } from 'react-router-dom'

import PureSolTechnologies from './components/PureSolTechnologies';

import FacebookFollowButton from './components/social/FacebookFollowButton';
import FacebookLikeButton from './components/social/FacebookLikeButton';

import GooglePlusAddOneButton from './components/social/GooglePlusAddOneButton';
import GooglePlusFollowButton from './components/social/GooglePlusFollowButton';
import GooglePlusShareButton from './components/social/GooglePlusShareButton';

import TweetButton from './components/social/TweetButton';
import TwitterFollowButton from './components/social/TwitterFollowButton';


export default function Footer() {
    return (
        <footer>
            <div className="row">
                <div className="col-md-4">
                    <PureSolTechnologies />
                </div>
                <div className="col-md-4">
                </div>
                <div className="col-md-4">
                    <Link to="/imprint">Imprint</Link>
                </div>
                <div className="col-md-4">
                    <TweetButton /><br />
                    <TwitterFollowButton />
                </div>
                <div className="col-md-4">
                    <GooglePlusAddOneButton /><br />
                    <GooglePlusFollowButton /><br />
                    <GooglePlusShareButton />
                </div>
                <div className="col-md-4">
                    <FacebookLikeButton /><br />
                    <FacebookFollowButton />
                </div>
            </div>
        </footer>
    );
}
