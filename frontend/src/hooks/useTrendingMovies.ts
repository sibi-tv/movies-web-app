import { useEffect, useState } from "react";
import apiClient from "../lib/apiClientImplementation"
import type { MovieSummary } from "../lib/types";

const useTrendingMovies = (window: 'day'|'week') => {

    const client = apiClient;
    const [trendingMovies, setTrendingMovies] = useState<MovieSummary[] | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const loadTrendingMovies = async () => {
        try { 
            setTrendingMovies(await client.getTrendingMovies(window)); 
        }
        catch (e) { 
            setError((e as Error).message ?? 'Failed to load trending'); 
        }
        finally{ 
            setLoading(false); 
        }
    };

    useEffect(() => { loadTrendingMovies() })

    return { trendingMovies, error, loading, loadTrendingMovies }
}

export default useTrendingMovies;