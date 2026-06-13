# Stock Assistant AI

AI-powered stock research assistant built using Spring Boot, Weaviate, Groq, and LangChain4j.

The application ingests stock-related PDFs (annual reports, concalls, presentations), stores them in a vector database, and allows users to analyze stocks and ask questions through a simple web UI.

## Features

* PDF document ingestion
* Semantic chunking
* Weaviate vector database integration
* AI-generated stock analysis
* Natural language Q&A
* Simple HTML, CSS, and JavaScript UI
* Spring Boot REST APIs

## Tech Stack

* Java 21
* Spring Boot
* Weaviate Cloud
* LangChain4j
* Groq LLM
* Apache PDFBox
* HTML/CSS/JavaScript

## APIs

### Analyze Stock

```http
POST /api/v1/chat/analyze
```

### Ask Question

```http
POST /api/v1/chat/ask
```

## Run Locally

Configure:

```properties
weaviate.host=
weaviate.api-key=
groq.api-key=
```

Start application:

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Architecture

```text
PDF → Chunking → Weaviate → Retrieval → LLM → UI
```

## UI

* Select Stock
* Generate Analysis
* Ask Questions
* View AI Responses
