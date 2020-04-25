const ApartmentsComponent = { template: '<guest-apartments></guest-apartments>'}

const router = new VueRouter({
    mode: 'hash',
    routes:[
        { path: '/', component: ApartmentsComponent }
    ]
});

var guestApp = new Vue({
    router,
	el: '#guestID'
});