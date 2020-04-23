const WebApartmnts = { template: '<web-apartments></web-apartments>'}
const ReservationCart = { template: '<reservation-cart></reservation-cart>'}
const RegistrationComponent = { template: '<app-register></app-register>'}


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: WebApartmnts},
		  { path: '/rc', component: ReservationCart},
		  { path: '/register', component: RegistrationComponent},
		  { path: '/login', component: RegistrationComponent}
		  
	  ]
});

var app = new Vue({
	router,
	el: '#application'
});

