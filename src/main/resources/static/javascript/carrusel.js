document.addEventListener("DOMContentLoaded", function() {
    const imagenPrincipal = document.querySelector(".imagen-principal");
    const miniaturas = document.querySelectorAll(".miniatura");
    const imagenes = Array.from(miniaturas);
    let indiceActual = 0;

    function mostrarImagen(indice) {
        if (imagenes[indice]) {
            imagenPrincipal.src = imagenes[indice].src;
            indiceActual = indice;
        }
    }

    miniaturas.forEach((miniatura, idx) => {
        miniatura.addEventListener("click", () => {
            mostrarImagen(idx);
        });
    });

    // Contenedor relativo solo para la imagen principal
    const contenedorImagen = document.createElement("div");
    contenedorImagen.style.position = "relative";
    contenedorImagen.style.display = "inline-block";
    imagenPrincipal.parentNode.insertBefore(contenedorImagen, imagenPrincipal);
    contenedorImagen.appendChild(imagenPrincipal);

    const botonPrev = document.createElement("button");
    const botonNext = document.createElement("button");
    botonPrev.innerHTML = "&#10094;";
    botonNext.innerHTML = "&#10095;";

    const estiloBoton = {
        position: "absolute",
        top: "50%",
        transform: "translateY(-50%)",
        background: "rgba(255,255,255,0.7)",
        color: "#333",
        border: "none",
        padding: "10px 15px",
        cursor: "pointer",
        fontSize: "1.5rem",
        borderRadius: "50%",
        boxShadow: "0 2px 6px rgba(0,0,0,0.2)",
        transition: "background 0.3s, transform 0.2s",
        zIndex: "10"
    };

    Object.assign(botonPrev.style, estiloBoton);
    Object.assign(botonNext.style, estiloBoton);

    botonPrev.style.left = "-15px";
    botonNext.style.right = "-15px";

    // Hover
    [botonPrev, botonNext].forEach(btn => {
        btn.addEventListener("mouseover", () => {
            btn.style.background = "rgba(255,255,255,0.95)";
            btn.style.transform = "translateY(-50%) scale(1.1)";
        });
        btn.addEventListener("mouseout", () => {
            btn.style.background = "rgba(255,255,255,0.7)";
            btn.style.transform = "translateY(-50%) scale(1)";
        });
    });

    contenedorImagen.appendChild(botonPrev);
    contenedorImagen.appendChild(botonNext);

    botonPrev.addEventListener("click", () => {
        indiceActual = (indiceActual - 1 + imagenes.length) % imagenes.length;
        mostrarImagen(indiceActual);
    });

    botonNext.addEventListener("click", () => {
        indiceActual = (indiceActual + 1) % imagenes.length;
        mostrarImagen(indiceActual);
    });

    if (imagenes.length > 0) {
        mostrarImagen(0);
    }
});