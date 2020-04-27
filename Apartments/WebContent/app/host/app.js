const UsersComponent = { template: '<host-users></host-users>'}
const HomeComponent = {template: '<host-home></host-home>'}
const ReservationComponent = {template: '<host-reservation></host-reservation>'}
const ProfileComponent = {template: '<host-profile></host-profile>'}
const ActiveApartmentsComponent = {template: '<host-ActiveApartments></host-ActiveApartments>'}
const InActiveApartmentsComponent = {template: '<host-InactiveApartments></host-InactiveApartments>'}
const CommentsAndReviewwsComponent = {template: '<host-CommentsAndReviews></host-CommentsAndReviews>'}

const router = new VueRouter({
    mode: 'hash',
    routes:[
        { path: '/users', component: UsersComponent },
        { path: '/home', component: HomeComponent},
        { path: '/reservations', component: ReservationComponent},
        { path: '/profile', component: ProfileComponent},
        { path: '/hostActiveApartments', component: ActiveApartmentsComponent},
        { path: '/hostInactiveApartments', component: InActiveApartmentsComponent},
        { path: '/commentsAndReviews', component: CommentsAndReviewwsComponent}
    ]
});

var hostApp = new Vue({
    router,
	el: '#hostID'
});