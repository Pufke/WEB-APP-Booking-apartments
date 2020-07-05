Vue.component("administrator-comments", {
    data() {
        return {
            comments: []
        }
    },
    template: `
    <div id = "styleForApartmentsView" >

        <ul>
            <li v-for="comment in comments">
                <h2> Author ID: {{ comment.guestAuthorOfCommentID}} </h2>
                <h2> Apartment ID: {{ comment.commentForApartmentID }} </h2>
                <h2> Text: {{ comment.txtOfComment }} </h2>
                <h2> Rating: {{ comment.ratingForApartment }} </h2>
            </li>
        </ul>

        <br>

        <!-- Table of comments on apartments -->
        <div class="styleForTable">
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> Text </th>
                        <th> Rating </th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="comment in comments">
                        <td> {{ comment.txtOfComment }} </td>
                        <td> {{ comment.ratingForApartment }} </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for comments on apartments -->

    </div>

    `,
    mounted() {
        axios
            .get('rest/comments/getComments')
            .then(response => {
                response.data.forEach(el => {
                    this.comments.push(el);
                });
                return this.comments;
            });
    },
});