const ApartmentsComponent = { template: '<guest-apartments></guest-apartments>'}
const HomeComponent = {template: '<guest-home></guest-home>'}
const ReservationComponent = {template: '<guest-reservation></guest-reservation>'}
const ProfileComponent = {template: '<guest-profile></guest-profile>'}
const LogoutComponent = {template: '<app-logout></app-logout>'}

const router = new VueRouter({
    mode: 'hash',
    routes:[
        { path: '/', component: ApartmentsComponent },
        { path: '/home', component: HomeComponent},
        { path: '/reservations', component: ReservationComponent},
        { path: '/profile', component: ProfileComponent},
        { path: '/logout', component: LogoutComponent},
    ]
});

var guestApp = new Vue({
    router,
	el: '#guestID'
});