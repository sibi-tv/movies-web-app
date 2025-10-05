import type { MovieDetails, MovieSummary } from './types'

interface MovieApiCalls {
  getTrendingMovies(window: 'day'|'week'): Promise<MovieSummary[]>
  getMovieDetails(id: number): Promise<MovieDetails>
}

export type { MovieApiCalls };