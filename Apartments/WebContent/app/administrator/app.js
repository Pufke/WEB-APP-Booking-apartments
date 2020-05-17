const HomeComponent = {template: '<administrator-home></administrator-home>'}
const ApartmentsComponent = { template: '<administrator-apartments></administrator-apartments>'}
const ProfileComponent = { template: '<administrator-profile></administrator-profile>'}
const UsersComponent = { template: '<administrator-users></administrator-users>'}
const ContentsComponent = { template: '<administrator-contents></administrator-contents>'}
const ReservationsComponent = { template: '<administrator-reservations></administrator-reservations>'}
const CommentsComponent = { template: '<administrator-comments></administrator-comments>'}


const router = new VueRouter({
    mode: 'hash',
    routes:[
        {path : '/', component: ApartmentsComponent},
        {path : '/apartments', component: ApartmentsComponent},
        {path : '/home', component: HomeComponent},
        {path : '/profile', component: ProfileComponent},
        {path : '/users', component: UsersComponent},
        {path : '/contents', component: ContentsComponent},
        {path : '/reservations', component: ReservationsComponent},
        {path : '/comments', component: CommentsComponent},
    ]
})

var adminApp = new Vue({
    router,
    el: '#administratorID'
});