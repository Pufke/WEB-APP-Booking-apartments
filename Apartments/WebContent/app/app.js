const WebApartmnts = { template: '<web-apartments></web-apartments>'}
const ReservationCart = { template: '<reservation-cart></reservation-cart>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		  { path: '/', component: WebApartmnts},
		  { path: '/rc', component: ReservationCart}
	  ]
});

var app = new Vue({
	router,
	el: '#application'
});

