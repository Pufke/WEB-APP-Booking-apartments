const ApartmentsComponent = { template: '<view-apartments></view-apartments>'}
const RegistrationComponent = { template: '<app-register></app-register>'}
const LoginComponent = { template: '<app-login></app-login>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: RegistrationComponent},
		  { path: '/apartments', component: ApartmentsComponent},
		  { path: '/register', component: RegistrationComponent},
		  { path: '/login', component: LoginComponent}
		  
	  ]
});

var app = new Vue({
	router,
	el: '#application'
});

