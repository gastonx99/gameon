console.log("betting-game-user.js");

export default class BettingGameUserWidget extends HTMLElement {
    static observedAttributes() {
        return ["game-status"];
    }

    constructor() {
        super();
        this.gamestatus = this.getAttribute("game-status")

        const linkElem = document.createElement('link');
        linkElem.setAttribute('rel', 'stylesheet');
        linkElem.setAttribute('href', '/css/main.css');
        const shadow = this.attachShadow({mode: 'open'});
        const div = document.createElement('div');
        div.className = "dashboard-widget";
        div.innerHTML = `
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
    <tbody id="tbody">
    </tbody>
    </table>
        `;
        const span = document.createElement("span");
        span.innerText = "Your " + this.gamestatus + " games";
        shadow.appendChild(linkElem);
        shadow.appendChild(span);
        shadow.appendChild(div);
        this.tableBody = shadow.getElementById("tbody");
    }

    connectedCallback() {
        this.fetch();
    }

    async fetch() {
        const url = "http://localhost:9080/api/game/user?gamestatus=" + this.gamestatus;
        console.log("Fetching url", url);
        const response = await fetch(url);
        const model = await response.json();

        console.log("Games: " + model.games.length);
        for (let game of model.games) {
            let tr = document.createElement("tr");
            tr.innerHTML = `
<tr>
    <td><a href="/usergame/${game.pk}">${game.name}</a></td>
    <td>4</td>
    <td>11</td>
    <td>2</td>
    <td>Ongoing, bets missing</td>
    <td>2020-09-14 15:00</td>
    <td>2021-05-24 15:00</td>
</tr>
            `;
            this.tableBody.appendChild(tr);
        }
        this.model = model;
    }

}

// Define the new element
customElements.define("go-bettinggameuserwidget", BettingGameUserWidget);

