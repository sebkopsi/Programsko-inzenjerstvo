Get-Content ..\.env | ForEach-Object {
   if ($_ -match '^\s*([^#][^=]+?)\s*=\s*(.*?)\s*$') {
     $name = $matches[1].Trim()
     $value = $matches[2].Trim()

    # Remove surrounding single or double quotes if present
    if (($value.StartsWith('"') -and $value.EndsWith('"')) -or
        ($value.StartsWith("'") -and $value.EndsWith("'"))) {
      $value = $value.Substring(1, $value.Length - 2)
    }

     Set-Item -Path "Env:$name" -Value $value
   }
 }

Write-Host "Environment variables loaded"

Get-ChildItem *.sql | Sort-Object Name | ForEach-Object {
   Write-Host "-----------------     Running $($_.Name)     -----------------"
   psql -h localhost -p 5432 `
        -U $env:DATABASE_USERNAME `
        -d $env:DATABASE_NAME `
        -f $_.FullName
 }

Write-Host "-----------------     All migrations applied :)     -----------------"