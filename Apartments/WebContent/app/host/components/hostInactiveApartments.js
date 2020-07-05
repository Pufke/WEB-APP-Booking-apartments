Vue.component("host-InactiveApartments", {
	data() {
		return {
            apartments: [],
            previewSort: false,
		}
	},

	template: 
	`
	<div id = "styleForApartmentsView">

        <button type="button" @click=" previewSort = !previewSort " class="btn"><i class="fa fa-sort" aria-hidden="true"></i> SORT </button>

        <br><br>
        <!-- Sort for apartments -->
        <div v-if="previewSort" class="sortInApp">
            <form method='post'>

                <button type="button" @click="sortAsc"><i class="fa fa-sort" aria-hidden="true"></i> PRICE UP</button>
                <button type="button" @click="sortDesc"><i class="fa fa-sort" aria-hidden="true"></i> PRICE DOWN</button>

            </form>
        </div>
        <!-- End sort for apartments -->

		<br><br>
		<ul>
			<li v-for="apartment in apartments">
				<h2> {{ apartment.typeOfApartment }} </h2>
				<h2> {{ apartment.pricePerNight}} </h2>

				<button type="button" @click="activateApartment(apartment)"> Activate </button>
			</li>
		</ul>
		
		<br>
		<!-- Table of host apartments -->
        <div class="styleForTable">
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> Status </th>
                        <th> Type </th>
                        <th> Price </th>
                        <th> Rooms </th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="apartment in apartments">
                        <td> {{ apartment.status }} </td>
                        <td> {{ apartment.typeOfApartment }} </td>
                        <td> {{ apartment.pricePerNight}} </td>
                        <td> {{ apartment.numberOfRooms}} </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for host apartments -->

	</div>
	
	`,
	methods: {
		activateApartment(apartment){
			axios
                .post('rest/apartments/activateApartment', {
                    addedApartment: apartment
                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "INACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success activation !!", "Success activation!");
                    return this.apartments;
                });
		},
        sortAsc: function(){
            let tempApartments = [];

            (this.apartments).forEach(element => tempApartments.push(element));
            tempApartments = this.multisort(tempApartments, ['pricePerNight', 'pricePerNight'], ['ASC','DESC']);

            this.apartments = tempApartments;

        },
        sortDesc: function(){
            let tempApartments = [];
            
            (this.apartments).forEach(element => tempApartments.push(element));
            tempApartments = this.multisort(tempApartments, ['pricePerNight', 'pricePerNight'], ['DESC','ASC']);

            this.apartments = tempApartments;
        },
        multisort: function(arr, columns, order_by) {
            if(typeof columns == 'undefined') {
                columns = []
                for(x=0;x<arr[0].length;x++) {
                    columns.push(x);
                }
            }

            if(typeof order_by == 'undefined') {
                order_by = []
                for(x=0;x<arr[0].length;x++) {
                    order_by.push('ASC');
                }
            }

            function multisort_recursive(a,b,columns,order_by,index) {  
                var direction = order_by[index] == 'DESC' ? 1 : 0;

                var is_numeric = !isNaN(a[columns[index]]-b[columns[index]]);

                var x = is_numeric ? a[columns[index]] : a[columns[index]].toLowerCase();
                var y = is_numeric ? b[columns[index]] : b[columns[index]].toLowerCase();

                if(!is_numeric) {
                    /*
                    *   If we have string, then convert it to
                    *   array of charachter with .split("")
                    *   then go through every ellement and 
                    *   get ascii value from it and add that to sum
                    *   of that word, with that, we have uniq value for every
                    *   word.
                    *    author: vaxi
                    */
                    let sum_x=0;
                    let sum_y=0;
                    
                    x.split("").forEach(element => sum_x += element.charCodeAt())
                    y.split("").forEach(element => sum_y += element.charCodeAt())

                    x= sum_x;
                    y=sum_y;
                }

                if(x < y) {
                        return direction == 0 ? -1 : 1;
                }

                if(x == y)  {
                    return columns.length-1 > index ? multisort_recursive(a,b,columns,order_by,index+1) : 0;
                }

                return direction == 0 ? 1 : -1;
            }

            return arr.sort(function (a,b) {
                return multisort_recursive(a,b,columns,order_by,0);
            });
        },
	},
	mounted() {
		axios
			.get('rest/apartments/getApartments')
			.then(response => {
				response.data.forEach(el => {
					if (el.status == "INACTIVE")
						this.apartments.push(el);
				});
				return this.apartments;
			})
	}


});