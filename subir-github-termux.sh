#!/usr/bin/env bash
set -e

git config --global user.name "adonias2222"

echo "Digite seu e-mail do GitHub:"
read -r EMAIL
git config --global user.email "$EMAIL"

git init
git add .
git commit -m "feat: projeto NutriConecta corrigido" || echo "Nada novo para commitar"
git branch -M main
git remote remove origin 2>/dev/null || true
git remote add origin https://github.com/adonias2222/nutriconecta.git

echo "Subindo para o GitHub..."
git push -u origin main --force
