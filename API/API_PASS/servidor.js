
const express = require('express');
const jwt = require('jsonwebtoken');


const app = express();

app.use(express.json());

// para validar tokens
const SECRET_KEY = 'pass1234';

// estamos a criar const apra simular 
const passwords = {};  // guarda pass
const apps = new Set(); // guarda os nomes das apps que criamos


// verificar se o utilizador está autenticado via JWT
function autenticarJWT(req, res, next) {
    const authHeader = req.headers.authorization;

    
    if (authHeader && authHeader.startsWith('Bearer ')) {
        const token = authHeader.split(' ')[1]; // extrai o token

        
        jwt.verify(token, SECRET_KEY, (err, user) => {
            if (err) return res.sendStatus(403); 
            req.user = user; // guarda as info do token 
            next(); 
        });
    } else {
        res.sendStatus(401); 
    }
}


// vai criar uma nova pass para uma app já existente
app.post('/password/:id', autenticarJWT, (req, res) => {
    const id = req.params.id;       // obtém o nome da app 
    const { password } = req.body;        // obtém a password 

    if (!apps.has(id))
        return res.status(404).json({ error: "App não existe" });

    passwords[id] = password;
    res.status(201).json({ message: 'Pass criada com sucesso.' });
});

// vamos att a pass já existente
app.put('/password/:id', autenticarJWT, (req, res) => {
    const id = req.params.id;
    const { password } = req.body;

    if (!apps.has(id))
        return res.status(404).json({ error: "App não existe" });

    passwords[id] = password;
    res.json({ message: 'Pass atualizada com sucesso.' });
});

// devolde a pass da app
app.get('/password/:id', autenticarJWT, (req, res) => {
    const id = req.params.id;

    if (!apps.has(id))
        return res.status(404).json({ error: "App não existe" });

    const password = passwords[id];
    res.json({ password });
});


// criaamos uma nova app
app.post('/app', autenticarJWT, (req, res) => {
    const { id } = req.body;

    if (!id)
        return res.status(400).json({ error: "id da app obrigatório" });

    if (apps.has(id))
        return res.status(400).json({ error: "App já existe" });

    apps.add(id);
    res.status(201).json({ message: 'App criada.' });
});

// Devolve a lista de apps criadas
app.get('/apps', autenticarJWT, (req, res) => {
    res.json(Array.from(apps));
});

const PORT = 3000;

// O servido url
app.listen(PORT, () => {
    console.log(`API runnig on url -> http://localhost:${PORT}`);
});
