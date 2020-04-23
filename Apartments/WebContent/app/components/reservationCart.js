Vue.component("reservation-cart", {
	data: function () {
		    return {
		      rc: null,
		      total: 0
		    }
	},
	template: ` 
<div>
		Apartment reservations:
		<table border="1">
		<tr bgcolor="lightgrey">
			<th>Name </th><th>Price for one night</th><th>Nights</th><th>Total price</th></tr>
			<tr v-for="i in rc">
			<td> {{i.apartment.name}}</td>
			<td> {{i.apartment.price}}</td>
			<td> {{i.count}} </td>
			<td> {{i.total}} </td>
			</tr>
		</table>
		<br /> 
		<button v-on:click="clearRc" >Delete it</button>
		<p>
		Total: {{total}} euros.
		</p>

	
</div>		  
`
	, 
	methods : {
		init : function() {
			this.rc = {};
			this.total = 0.0;
		}, 
		clearRc : function () {
			if (confirm('Are you sure?') == true) {
				axios
		          .post('rest/apartments/clearRc')
		          .then(response => (this.init()))
			}
		} 
	},
	mounted () {
        axios
          .get('rest/apartments/getJustRc')
          .then(response => (this.rc = response.data));
        axios
        .get('rest/apartments/getTotal')
        .then(response => (this.total = response.data));
    }
	//TODO: Staviti kasnije kad napravimo reservationCartService, da gadjamo rest/reservation/blablabla..
});