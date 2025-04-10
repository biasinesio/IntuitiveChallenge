<template>
  <div class="container">
    <h1>Busca de Operadoras</h1>
    <input v-model="busca" @keyup.enter="buscarOperadoras" placeholder="Digite um termo (ex: Unimed)" />
    <button @click="buscarOperadoras">Buscar</button>

    <div v-if="resultado.length">
      <h2>Resultados:</h2>
      <p>Itens carregados: {{ resultado.length }}</p>
      <ul>
        <li v-for="rs in resultado" :key="rs.CNPJ">
          <strong>{{ rs.Nome_Fantasia }}</strong> - {{ rs.Cidade }} ({{ rs.UF }})
        </li>
      </ul>
    </div>

    <div v-else-if="buscou">
      <p>Nenhum resultado encontrado.</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'


const busca = ref('')
const resultado = ref([])
const buscou = ref(false)

const buscarOperadoras = async () => {
  if (!busca.value) return;

  try {
    const response = await axios.get(`http://127.0.0.1:5000/buscar?q=${encodeURIComponent(busca.value)}`);
    console.log('Tipo:', typeof response.data);              
    console.log('É array?', Array.isArray(response.data));   
    console.log('Primeiro item:', response.data[0]);         

    resultado.value = response.data;
    buscou.value = true;
  } catch (error) {
    console.error('Erro ao buscar:', error);
    resultado.value = [];
    buscou.value = true;
  }
};


</script>

<style>
.container {
  max-width: 600px;
  margin: 50px auto;
  font-family: sans-serif;
}

p {
  background-color: black;
}

input {
  padding: 8px;
  width: 70%;
  margin-right: 10px;
}

button {
  padding: 8px 16px;
}

ul {
  margin-top: 20px;
  list-style: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
  padding: 10px;
}
</style>