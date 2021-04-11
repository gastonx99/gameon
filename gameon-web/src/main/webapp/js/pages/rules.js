console.log("about.js");

export default class Rules extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
<h1>Rules</h1>
<ul>
    <li>1 point for correct result</li>
    <li>2 point for correct score</li>
</ul>
    `;
    }
}

customElements.define("go-rules", Rules);

