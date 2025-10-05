type MovieSummary = {
  id: number
  title: string
  imageUrl: string
  voteAverage?: number
  releaseDate: string
}

type MovieDetails = {
  id: number
  title: string
  overview: string
  genres: string[]
  runtime: number
  releaseDate: string
  imageUrl: string
  voteAverage?: number
}

export type {MovieSummary, MovieDetails};