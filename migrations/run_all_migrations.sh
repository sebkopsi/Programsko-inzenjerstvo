# environment: bash
set -a && source ../.env && set +a
# running migrations
for file in $(ls *.sql | sort); do
  echo "---------------     Running $file     ---------------"
  psql -h localhost -p 5432 \
       -U "$DATABASE_USERNAME" \
       -d "$DATABASE_NAME" \
       -f "$file"
done
echo "-----------------     ✅ All migrations have been run.     -----------------"