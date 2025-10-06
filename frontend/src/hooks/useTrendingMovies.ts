import { useCallback, useEffect, useState } from "react";
import apiClient from "../lib/apiClientImplementation"
import type { MovieSummary } from "../lib/types";

const useTrendingMovies = (window: 'day'|'week') => {

    const [trendingMovies, setTrendingMovies] = useState<MovieSummary[] | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const loadTrendingMovies = useCallback( async () => {
        try { 
            setTrendingMovies(await apiClient.getTrendingMovies(window)); 
        }
        catch (e) { 
            setError((e as Error).message ?? 'Failed to load trending movies'); 
        }
        finally{ 
            setLoading(false); 
        }
    },  [window]);

    useEffect(() => { 
        setLoading(true);
        loadTrendingMovies() 
    }, [loadTrendingMovies]);

    return { trendingMovies, error, loading, loadTrendingMovies }
}

export default useTrendingMovies;