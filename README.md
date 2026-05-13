# todo-app

Application todo full stack.

## Structure

```
todo-app/
├── backend/    # API REST Spring Boot (Java 21, Gradle)
└── frontend/   # SPA Angular (à venir — voir MOU-46)
```

Chaque sous-projet est autonome côté tooling : pas de `package.json` racine, pas
d'orchestrateur de build cross-projet.

## Backend

Voir [`backend/`](backend/) pour le détail. Démarrage rapide :

```bash
cd backend
./gradlew bootRun
```

L'API écoute sur `http://localhost:8080`.

## Frontend

Pas encore initialisé. Une fois le projet Angular en place dans `frontend/`,
démarrage :

```bash
cd frontend
npm install
npm start
```

## Docker Compose

Un `docker-compose.yml` à la racine permettra de lancer backend + frontend
ensemble. Pour l'instant seul le backend y est déclaré.

```bash
docker compose up --build
```
