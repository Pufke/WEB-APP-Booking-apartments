Vue.component("view-apartments",{
    data() {
        return {
            apartments: {}
        }
    },

    template:`
    <div>
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
    mounted() {
        axios
        .get('rest/apartments/getApartments')
        .then( response => {
            this.apartments = response.data;
        });
    },
})