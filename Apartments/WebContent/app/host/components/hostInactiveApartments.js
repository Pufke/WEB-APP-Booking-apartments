Vue.component("host-InactiveApartments",{
	data(){
        return { 
            apartments: []
        }
    },
    
    template: `
		    <div id = "styleForApartmentsView">

		        <ul>
		            <li v-for="apartment in apartments">
		                <h2> {{ apartment.typeOfApartment }} </h2>
		                <h2> {{ apartment.pricePerNight}} </h2>
		            </li>
		        </ul>
		        
		        <br>
		        <table border="1">
		        <tr bgcolor="lightgrey">
		            <th> Status </th><th> Type </th><th> Price </th><th> Rooms </th></tr>
		            <tr v-for="apartment in apartments">
		                <td> {{ apartment.status }} </td>
		                <td> {{ apartment.typeOfApartment }} </td>
		                <td> {{ apartment.pricePerNight}} </td>
		                <td> {{ apartment.numberOfRooms}} </td>
		            </tr>
		        </table>

		    </div>
		    
		    `,
		    mounted(){
		        axios
		        .get('rest/apartments/getApartments')
		        .then( response=> {
		        	response.data.forEach(el => {
		        		if(el.status == "INACTIVE")
		        			this.apartments.push(el);
		        		});
		        	return this.apartments;
		        })
		    }


		});