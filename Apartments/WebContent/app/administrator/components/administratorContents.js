Vue.component("administrator-contents",{
    data() {
        return {
            amenities: []
        }
    },
    template:`
    <div id = "styleForApartmentsView" >
        <h1> Hello from contents </h1>


        <ul>
            <li v-for="item in amenities">
                <h2> {{ item.amenitiesID }} </h2>
                <h2> {{ item.name }} </h2>
            </li>
        </ul>

        <br>
        <table border="1">
            <tr bgcolor="lightgrey">
                <th> ID </th>
                <th> name </th> 
            </tr>
            <tr v-for="item in amenities">
                <td> {{ item.amenitiesID }} </td>
                <td> {{ item.name}} </td>

            </tr>
        </table>

    </div>

    `,
    methods: {
        
    },
    mounted() {
        axios
        .get('rest/amenities/getAmenities')
        .then( response => {
        	response.data.forEach(el => {
        		this.amenities.push(el);
        	});
        	return this.amenities;
        });
    },
});