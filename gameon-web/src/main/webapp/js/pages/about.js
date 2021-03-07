console.log("about.js");

export default class AboutUs extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
      <div class="page">
        <h1>About Us</h1>
      </div>
    `;
    }
}

customElements.define("go-about", AboutUs);

