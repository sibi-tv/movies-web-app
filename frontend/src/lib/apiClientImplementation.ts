import type { MovieApiCalls } from './apiClient'
import type { MovieDetails, MovieSummary } from './types'

const apiClient: MovieApiCalls = {

  async getTrendingMovies(window: 'day'|'week' = 'day'): Promise<MovieSummary[]> {
    const res = await fetch(`/api/trending/movie?window=${window}`)
    if (!res.ok) throw new Error(`Trending failed: ${res.status}`)
    return await res.json()
  },

  async getMovieDetails(id: number): Promise<MovieDetails> {
    const res = await fetch(`/api/movies/${id}`)
    if (!res.ok) throw new Error(`Movie failed: ${res.status}`)
    return await res.json()
  },
  
}

export default apiClient;