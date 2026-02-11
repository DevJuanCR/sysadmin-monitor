const API_URL = "/api/metrics";
const INTERVALO = 5000; // cada 5 segundos igual que el agente Python

const statusEl = document.getElementById("status");
const cpuValueEl = document.getElementById("cpu-value");
const ramValueEl = document.getElementById("ram-value");

// configuracion comun para los dos graficos
function crearConfig(label, color) {
    return {
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: label,
                data: [],
                borderColor: color,
                backgroundColor: color + "20",
                borderWidth: 2,
                fill: true,
                tension: 0.3,
                pointRadius: 2
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    min: 0,
                    max: 100,
                    ticks: { color: "#888", callback: v => v + "%" },
                    grid: { color: "#2a2d3a" }
                },
                x: {
                    ticks: { color: "#888", maxTicksLimit: 10 },
                    grid: { color: "#2a2d3a" }
                }
            },
            plugins: {
                legend: { display: false }
            },
            animation: { duration: 300 }
        }
    };
}

// creamos los dos graficos
const cpuChart = new Chart(
    document.getElementById("cpuChart"),
    crearConfig("CPU", "#38bdf8")
);

const ramChart = new Chart(
    document.getElementById("ramChart"),
    crearConfig("RAM", "#a78bfa")
);

// formateamos el timestamp para que se lea bien en el eje X
function formatearHora(timestamp) {
    const fecha = new Date(timestamp);
    const h = String(fecha.getHours()).padStart(2, "0");
    const m = String(fecha.getMinutes()).padStart(2, "0");
    const s = String(fecha.getSeconds()).padStart(2, "0");
    return h + ":" + m + ":" + s;
}

// actualizamos un grafico con los datos nuevos
function actualizarGrafico(chart, labels, datos) {
    chart.data.labels = labels;
    chart.data.datasets[0].data = datos;
    chart.update();
}

// pedimos las metricas al backend y actualizamos todo
async function fetchMetricas() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Status " + response.status);
        }

        const metricas = await response.json();

        if (metricas.length === 0) {
            statusEl.textContent = "Esperando datos del agente...";
            statusEl.className = "";
            return;
        }

        // sacamos las horas y los valores de cada metrica
        const labels = metricas.map(m => formatearHora(m.timestamp));
        const cpuData = metricas.map(m => m.cpuUsage);
        const ramData = metricas.map(m => m.ramUsage);

        // actualizamos los graficos
        actualizarGrafico(cpuChart, labels, cpuData);
        actualizarGrafico(ramChart, labels, ramData);

        // actualizamos los valores grandes con la ultima lectura
        const ultima = metricas[metricas.length - 1];
        cpuValueEl.textContent = ultima.cpuUsage.toFixed(1) + "%";
        ramValueEl.textContent = ultima.ramUsage.toFixed(1) + "%";

        statusEl.textContent = "Conectado";
        statusEl.className = "connected";

    } catch (error) {
        statusEl.textContent = "Error de conexion";
        statusEl.className = "error";
    }
}

// primera carga y luego cada 5 segundos
fetchMetricas();
setInterval(fetchMetricas, INTERVALO);