async function analyzeStock() {

    const stock =
        document.getElementById("stockSelect").value;

    const response = await fetch(
        "/api/v1/chat/analyze",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                stock: stock
            })
        }
    );

    const answer = await response.text();

    document.getElementById("analysis").innerHTML =
        answer;
}

async function askQuestion() {

    const stock =
        document.getElementById("stockSelect").value;

    const question =
        document.getElementById("question").value;

    const response = await fetch(
        "/api/v1/chat/ask",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                stock: stock,
                question: question
            })
        }
    );

    const answer = await response.text();

    document.getElementById("chat").innerHTML += `
        <div class="user-msg">
            <b>You:</b> ${question}
        </div>

        <div class="ai-msg">
            <b>AI:</b> ${answer}
        </div>
    `;
}