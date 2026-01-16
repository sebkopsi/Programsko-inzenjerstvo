database:
	cd migrations; devbox services up postgresql

frontend-local:
	cd frontend; devbox run local

backend-local:
	cd backend; devbox run local --env-file ../.env


localdev:
	process-compose up database frontend backend
update-types:
	cd frontend; npm install --save-dev @types/react-router
