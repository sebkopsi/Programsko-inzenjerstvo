run-backend-dev:
	cd backend; mvn spring-boot:run

run-frontend-dev:
	cd frontend; npm run dev

update-types:
	cd frontend; npm install --save-dev @types/react-router
