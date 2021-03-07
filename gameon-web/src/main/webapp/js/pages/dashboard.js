export default class Dashboard extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
<div class="page">
    <div>
        <span>Your active games</span>
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Participants</th>
                <th>Points</th>
                <th>Position</th>
                <th>Status</th>
                <th>Start</th>
                <th>End</th>
            </tr>
            </thead>
            <tbody id="body">
            <tr>
                <td><a route="/usergame/17">Gurras Premier League 2020/2021</a></td>
                <td>4</td>
                <td>11</td>
                <td>2</td>
                <td>Ongoing, bets missing</td>
                <td>2020-09-14 15:00</td>
                <td>2021-05-24 15:00</td>
            </tr>
            <tr>
                <td>Kalles World Cup 2022</td>
                <td>4</td>
                <td></td>
                <td></td>
                <td>Not yet started</td>
                <td>2022-06-24 15:00</td>
                <td>2022-07-21 19:00</td>
            </tr>
            </tbody>
        </table>
    </div>

    <div>
        <span>Your completed games</span>
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Participants</th>
                <th>Points</th>
                <th>Position</th>
                <th>Status</th>
                <th>Start</th>
                <th>End</th>
            </tr>
            </thead>
            <tbody id="body">
            <tr>
                <td>Gurras Premier League 2019/2020</td>
                <td>4</td>
                <td>54</td>
                <td>1</td>
                <td>Completed</td>
                <td>2019-09-14 15:00</td>
                <td>2020-05-24 15:00</td>
            </tr>
            <tr>
                <td>Kalles World Cup 2018</td>
                <td>4</td>
                <td></td>
                <td></td>
                <td>Cancelled</td>
                <td>2018-06-24 15:00</td>
                <td>2018-07-21 19:00</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
    `;
    }
}

customElements.define("go-dashboard", Dashboard);
