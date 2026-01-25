database:
	cd migrations; devbox services up postgresql

frontend-local:
	cd frontend; devbox run local

backend-local:
	cd backend; devbox run local --env-file ../.env

fileserver-local:
	cd fileserver; devbox run start

localdev:
	process-compose up database frontend backend fileserver
	
update-types:
	cd frontend; npm install --save-dev @types/react-router
