console.log("betting-game-user.js");

export default class BettingGameUser extends HTMLElement {
    static observedAttributes() {
        return ["id"];
    }

    connectedCallback() {
        console.log("ConnectedCallback: BettingGameUser");
        this.innerHTML = `
<div id="betting-game-user-header"></div>

<div>
    <span>Rules</span>
    <ul>
        <li>1 point for correct result</li>
        <li>2 point for correct score</li>
    </ul>
</div>

<form id="form-betting-game-user">
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
        <tbody id="betting-game-user-tbody">
        </tbody>
    </table>
</form>    

<input id="save-betting-game-user" type="button" value="Save">
<input type="button" value="Back">
<input id="reset-betting-game-user" type="button" value="Reset">
        `;

        this.form = document.getElementById("form-betting-game-user");
        this.saveButton = document.getElementById("save-betting-game-user");
        this.resetButton = document.getElementById("reset-betting-game-user");
        this.bettingGameUserHeader = document.getElementById("betting-game-user-header");
        this.tableBody = document.getElementById("betting-game-user-tbody");

        this.usergamePk = this.location.params.id;
        if (this.usergamePk && this.usergamePk !== null) {
            this.fetch();
        } else {
            console.error("No user game pk!");
        }

        const instance = this;
        this.saveButton.addEventListener('click', () => {
            instance.saveGame();
        })
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

        this.bettingGameUserHeader.innerHTML = `
            <span${model.name}</span>
            <br/>
            <span>${model.tournament} ${model.season}</span>
            <br/>
            <span>${model.bettingGameOwner}</span>
        `;

        console.log("Bets: " + model.bets.length);
        for (let bet of model.bets) {
            let tr = document.createElement("tr");
            tr.innerHTML = `
                <input type="hidden" name="bet_pk" value="${bet.pk}"/>
                <td>${bet.match.matchStart}</td>
                <td>${bet.match.homeTeam.name}</td>
                <td>${bet.match.awayTeam.name}</td>
                <td><input type="text" size="4" value="${bet.homeScore}" name="homescore"></td>
                <td><input type="text" size="4" value="${bet.awayScore}" name="awayscore"></td>
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

