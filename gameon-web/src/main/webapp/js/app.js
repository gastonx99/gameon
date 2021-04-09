import {Router} from './vaadin-router-1.7.4.js';

// setup a Router instance
const outlet = document.getElementById('outlet');
const router = new Router(outlet);
router.setRoutes([{
    path: '/',
    animate: true,
    children: [
        {path: '/', component: 'go-home'},
        {path: '/about', component: 'go-about'},
        {path: '/dashboard', component: 'go-dashboard'},
        {path: '/usergame/:id', component: 'go-bettinggameuser'},
        {path: '/teams', component: 'go-teams'},
        {path: '/team/:id', component: 'go-teamdetails'},
        {path: '*', component: 'go-notfound'}
    ]
}]);
