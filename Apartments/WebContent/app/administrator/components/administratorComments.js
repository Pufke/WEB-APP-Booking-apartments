Vue.component("administrator-comments", {
    data() {
        return {
            comments: []
        }
    },
    template: `
    <div id = "styleForApartmentsView" >
        <h1> Hello from comments </h1>

        <ul>
            <li v-for="comment in comments">
                <h2> Author ID: {{ comment.guestAuthorOfCommentID}} </h2>
                <h2> Apartment ID: {{ comment.commentForApartmentID }} </h2>
                <h2> Text: {{ comment.txtOfComment }} </h2>
                <h2> Rating: {{ comment.ratingForApartment }} </h2>
            </li>
        </ul>

        <br>
        <table border="1">
            <tr bgcolor="lightgrey">
                <th> Text </th>
                <th> Rating </th>
            </tr>
            <tr v-for="comment in comments">
                <td> {{ comment.txtOfComment }} </td>
                <td> {{ comment.ratingForApartment }} </td>
            </tr>
        </table>

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