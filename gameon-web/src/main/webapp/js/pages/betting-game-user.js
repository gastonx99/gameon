console.log("betting-game-user.js");

export default class BettingGameUser extends HTMLElement {
    static observedAttributes() {
        return ["id"];
    }

    constructor() {
        super();

        const linkElem = document.createElement('link');
        linkElem.setAttribute('rel', 'stylesheet');
        linkElem.setAttribute('href', '/css/main.css');
        const div = document.createElement('div');
        div.className = "betting-game-user";
        div.innerHTML = `
<div id="header"></div>

<form id="form">
    <table>
        <thead>
        <tr>
            <th>Date/time</th>
            <th colspan="2">Teams</th>
            <th colspan="2"></th>
            <th colspan="2">Final</th>
            <th>Points</th>
        </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
    </table>
</form>

<input id="save-button" type="button" value="Save">
<input id="back-button" type="button" value="Back">
<input id="reset-button" type="button" value="Reset">
        `;
        const shadow = this.attachShadow({mode: 'open'});
        shadow.appendChild(linkElem);
        shadow.appendChild(div);

        this.header = shadow.getElementById("header");
        this.form = shadow.getElementById("form");
        this.saveButton = shadow.getElementById("save-button");
        this.resetButton = shadow.getElementById("reset-button");
        this.tableBody = shadow.getElementById("tbody");

        const instance = this;
        this.saveButton.addEventListener('click', () => {
            instance.saveGame();
        })
    }

    connectedCallback() {
        console.log("ConnectedCallback: BettingGameUser");
        this.usergamePk = this.location.params.id;
        if (this.usergamePk && this.usergamePk !== null) {
            this.fetch();
        } else {
            console.error("No user game pk!");
        }
    }

    async saveGame() {
        console.log("Save game");
        const url = "/api/game/user/" + this.usergamePk;
        console.log("Save game using url", url);
        const formdata = new FormData(this.form);
        const response = await fetch(url, {
            method: 'POST',
            body: formdata
        });
        const model = await response.json();
        console.log("Respone model", model);
    }

    async fetch() {
        const url = "/api/game/user/" + this.usergamePk;
        console.log("Fetching url", url);
        const response = await fetch(url);
        const model = await response.json();

        this.header.innerHTML = `
            <span${model.name}</span>
            <br/>
            <span>${model.tournament} ${model.season}</span>
            <br/>
            <span>${model.bettingGameOwner}</span>
        `;

        console.log("Bets: " + model.bets.length);
        let previousStageIndex = -1;
        let previousRoundIndex = -1;

        for (let bet of model.bets) {
            if (bet.match.stageIndex != previousStageIndex) {
                let tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="8" class="group-header">${bet.match.stage}</td>
                `;
                this.tableBody.appendChild(tr);
                previousStageIndex = bet.match.stageIndex;
            }
            if (bet.match.roundIndex != previousRoundIndex) {
                let roundPrefix = "";
                if (bet.match.stageIndex == 0) {
                    roundPrefix = "Group " + bet.match.group + " - Round ";
                } else if (bet.match.stageIndex == 1 && !isNaN(bet.match.round)) {
                    roundPrefix = "Round ";
                }
                let tr = document.createElement("tr");
                tr.innerHTML = `
                    <td colspan="8" class="round-header">${roundPrefix}${bet.match.round}</td>
                `;
                this.tableBody.appendChild(tr);
                previousRoundIndex = bet.match.roundIndex;
            }
            let tr = document.createElement("tr");
            tr.innerHTML = `
                <input type="hidden" name="bet_pk" value="${bet.pk}"/>
                <td>${bet.match.matchStart}</td>
                <td>${bet.match.homeTeam.name}</td>
                <td>${bet.match.awayTeam.name}</td>
                <td><input type="text" size="2" value="${bet.homeScore}" name="homescore"></td>
                <td><input type="text" size="2" value="${bet.awayScore}" name="awayscore"></td>
                <td>${bet.match.finalHomeScore}</td>
                <td>${bet.match.finalAwayScore}</td>
                <td>3</td>
            `;
            this.tableBody.appendChild(tr);
        }
        this.model = model;
    }

    disconnectedCallback() {
        console.log("DisconnectedCallback: BettingGameUser");
    }

}

// Define the new element
customElements.define("go-bettinggameuser", BettingGameUser);

